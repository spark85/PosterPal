package com.google.zxing.integration.android;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

/**
 * <p>A utility class which helps ease integration with Barcode Scanner via {@link Intent}s. This is a simple
 * way to invoke barcode scanning and receive the result, without any need to integrate, modify, or learn the
 * project's source code.</p>
 *  *
 * <h2>Initiating a barcode scan</h2>
 *  *
 * <p>To integrate, create an instance of {@code IntentIntegrator} and call {@link #initiateScan()} and wait
 * for the result in your app.</p>
 *  *
 * <p>It does require that the Barcode Scanner (or work-alike) application is installed. The
 * {@link #initiateScan()} method will prompt the user to download the application, if needed.</p>
 *  *
 * <p>There are a few steps to using this integration. First, your {@link Activity} must implement
 * the method {@link Activity#onActivityResult(int, int, Intent)} and include a line of code like this:</p>
 *  *
 * <pre>{@code
 * public void onActivityResult(int requestCode, int resultCode, Intent intent) {
 *   IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
 *   if (scanResult != null) {
 *     // handle scan result
 *   }
 *   // else continue with any other code you need in the method
 *   ...
 * }
 * }</pre>
 *  *
 * <p>This is where you will handle a scan result.</p>
 *  *
 * <p>Second, just call this in response to a user action somewhere to begin the scan process:</p>
 *  *
 * <pre>{@code
 * IntentIntegrator integrator = new IntentIntegrator(yourActivity);
 * integrator.initiateScan();
 * }</pre>
 *  *
 * <p>Note that {@link #initiateScan()} returns an {@link AlertDialog} which is non-null if the
 * user was prompted to download the application. This lets the calling app potentially manage the dialog.
 * In particular, ideally, the app dismisses the dialog if it's still active in its {@link Activity#onPause()}
 * method.</p>
 * 
 * <p>You can use {@link #setTitle(String)} to customize the title of this download prompt dialog (or, use
 * {@link #setTitleByID(int)} to set the title by string resource ID.) Likewise, the prompt message, and
 * yes/no button labels can be changed.</p>
 * 
 * <p>By default, this will only allow applications that are known to respond to this intent correctly
 * do so. The apps that are allowed to response can be set with {@link #setTargetApplications(Collection)}.
 * For example, set to {@link #TARGET_BARCODE_SCANNER_ONLY} to only target the Barcode Scanner app itself.</p>
 *  *
 * <h2>Sharing text via barcode</h2>
 *  *
 * <p>To share text, encoded as a QR Code on-screen, similarly, see {@link #shareText(CharSequence)}.</p>
 *  *
 * <p>Some code, particularly download integration, was contributed from the Anobiit application.</p>
 *  *
 * @author Sean Owen
 * @author Fred Lin
 * @author Isaac Potoczny-Jones
 * @author Brad Drehmer
 * @author gcstang
 */
public class IntentIntegrator {
   public static final int REQUEST_CODE = 0x0000c0de;
   
   private static final String TAG = IntentIntegrator.class.getSimpleName();
   
   public static final String DEFAULT_TITLE = "Install Barcode Scanner?";
   
   public static final String DEFAULT_MESSAGE = "This application requires Barcode Scanner. Would you like to install it?";
   
   public static final String DEFAULT_YES = "Yes";
   
   public static final String DEFAULT_NO = "No";
   
   private static final String BS_PACKAGE = "com.google.zxing.client.android";
   
   public static final Collection<String> PRODUCT_CODE_TYPES = list("UPC_A", "UPC_E", "EAN_8", "EAN_13", "RSS_14");
   
   public static final Collection<String> ONE_D_CODE_TYPES = list("UPC_A", "UPC_E", "EAN_8", "EAN_13", "CODE_39", "CODE_93", "CODE_128",
              "ITF", "RSS_14", "RSS_EXPANDED");
   
   public static final Collection<String> QR_CODE_TYPES = Collections.singleton("QR_CODE");
   
   public static final Collection<String> DATA_MATRIX_TYPES = Collections.singleton("DATA_MATRIX");
   
   public static final Collection<String> ALL_CODE_TYPES = null;
   
   public static final Collection<String> TARGET_BARCODE_SCANNER_ONLY = Collections.singleton(BS_PACKAGE);
   
   public static final Collection<String> TARGET_ALL_KNOWN = list(
             BS_PACKAGE, // Barcode Scanner
             "com.srowen.bs.android", // Barcode Scanner+
             "com.srowen.bs.android.simple" // Barcode Scanner+ Simple
             // TODO add more -- what else supports this intent?
         );
   
   private final Activity activity;
   private String title;
   
   public void setTitle(String value) {
      this.title = value;
   }
   
   public String getTitle() {
      return this.title;
   }
   
   private String message;
   
   public void setMessage(String value) {
      this.message = value;
   }
   
   public String getMessage() {
      return this.message;
   }
   
   private String buttonYes;
   
   public void setButtonYes(String value) {
      this.buttonYes = value;
   }
   
   public String getButtonYes() {
      return this.buttonYes;
   }
   
   private String buttonNo;
   
   public void setButtonNo(String value) {
      this.buttonNo = value;
   }
   
   public String getButtonNo() {
      return this.buttonNo;
   }
   
   private Collection<String> targetApplications;
   
   public void setTargetApplications(Collection<String> value) {
      this.targetApplications = value;
   }
   
   public Collection<String> getTargetApplications() {
      return this.targetApplications;
   }
   
   public void setTitleByID(int titleID) {
      title = activity.getString(titleID);
        }
   
   public void setMessageByID(int messageID) {
      message = activity.getString(messageID);
        }
   
   public void setButtonYesByID(int buttonYesID) {
      buttonYes = activity.getString(buttonYesID);
        }
   
   public void setButtonNoByID(int buttonNoID) {
      buttonNo = activity.getString(buttonNoID);
        }
   
   public void setSingleTargetApplication(String targetApplication) {
      this.targetApplications = Collections.singleton(targetApplication);
        }
   
   /**
    * Initiates a scan for all known barcode types.
    */
   public AlertDialog initiateScan() {
      return initiateScan(ALL_CODE_TYPES);
        }
   
   private AlertDialog showDownloadDialog() {
      AlertDialog.Builder downloadDialog = new AlertDialog.Builder(activity);
       downloadDialog.setTitle(title);
       downloadDialog.setMessage(message);
       downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {
           Uri uri = Uri.parse("market://details?id=" + BS_PACKAGE);
           Intent intent = new Intent(Intent.ACTION_VIEW, uri);
           try {
             activity.startActivity(intent);
           } catch (ActivityNotFoundException anfe) {
             // Hmm, market is not installed
             Log.w(TAG, "Android Market is not installed; cannot install Barcode Scanner");
           }
         }
       });
       downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {}
       });
       return downloadDialog.show();
        }
   
   private static Collection<String> list (String... values) 
   {
      return Collections.unmodifiableCollection(Arrays.asList(values));
        }
   public IntentIntegrator (Activity activity) {
      this.activity = activity;
       title = DEFAULT_TITLE;
       message = DEFAULT_MESSAGE;
       buttonYes = DEFAULT_YES;
       buttonNo = DEFAULT_NO;
       targetApplications = TARGET_ALL_KNOWN;
        }
   
   /**
    * Initiates a scan only for a certain set of barcode types, given as strings corresponding
    *    * to their names in ZXing's {@code BarcodeFormat} class like "UPC_A". You can supply constants
    *    * like {@link #PRODUCT_CODE_TYPES} for example.
    */
   public AlertDialog initiateScan(Collection <String> desiredBarcodeFormats) {
      Intent intentScan = new Intent(BS_PACKAGE + ".SCAN");
       intentScan.addCategory(Intent.CATEGORY_DEFAULT);
      
       // check which types of codes to scan for
       if (desiredBarcodeFormats != null) {
         // set the desired barcode types
         StringBuilder joinedByComma = new StringBuilder();
         for (String format : desiredBarcodeFormats) {
           if (joinedByComma.length() > 0) {
             joinedByComma.append(',');
           }
           joinedByComma.append(format);
         }
         intentScan.putExtra("SCAN_FORMATS", joinedByComma.toString());
       }
      
       String targetAppPackage = findTargetAppPackage(intentScan);
       if (targetAppPackage == null) {
         return showDownloadDialog();
       }
       intentScan.setPackage(targetAppPackage);
       intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
       startActivityForResult(intentScan, REQUEST_CODE);
       return null;
        }
   
   /**
    * Start an activity.<br>
    *    * This method is defined to allow different methods of activity starting for
    *    * newer versions of Android and for compatibility library.
    *    *
    *    * @param intent Intent to start.
    *    * @param code Request code for the activity
    *    * @see android.app.Activity#startActivityForResult(Intent, int)
    *    * @see android.app.Fragment#startActivityForResult(Intent, int)
    */
   protected void startActivityForResult(Intent intent, int code) {
      activity.startActivityForResult(intent, code);
        }
   
   private String findTargetAppPackage(Intent intent) {
      PackageManager pm = activity.getPackageManager();
       List<ResolveInfo> availableApps = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
       if (availableApps != null) {
         for (ResolveInfo availableApp : availableApps) {
           String packageName = availableApp.activityInfo.packageName;
           if (targetApplications.contains(packageName)) {
             return packageName;
           }
         }
       }
       return null;
        }
   
   /**
    * <p>Call this from your {@link Activity}'s
    *    * {@link Activity#onActivityResult(int, int, Intent)} method.</p>
    *    *
    *    * @return null if the event handled here was not related to this class, or
    *    *  else an {@link IntentResult} containing the result of the scan. If the user cancelled scanning,
    *    *  the fields will be null.
    */
   public static IntentResult parseActivityResult(int requestCode, int resultCode, Intent intent) {
      if (requestCode == REQUEST_CODE) {
         if (resultCode == Activity.RESULT_OK) {
           String contents = intent.getStringExtra("SCAN_RESULT");
           String formatName = intent.getStringExtra("SCAN_RESULT_FORMAT");
           byte[] rawBytes = intent.getByteArrayExtra("SCAN_RESULT_BYTES");
           int intentOrientation = intent.getIntExtra("SCAN_RESULT_ORIENTATION", Integer.MIN_VALUE);
           Integer orientation = intentOrientation == Integer.MIN_VALUE ? null : intentOrientation;
           String errorCorrectionLevel = intent.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");
           return new IntentResult(contents,
                                   formatName,
                                   rawBytes,
                                   orientation,
                                   errorCorrectionLevel);
         }
         return new IntentResult();
       }
       return null;
        }
   
   /**
    * Shares the given text by encoding it as a barcode, such that another user can
    *    * scan the text off the screen of the device.
    *    *
    *    * @param text the text string to encode as a barcode
    */
   public void shareText(CharSequence text) {
      Intent intent = new Intent();
       intent.addCategory(Intent.CATEGORY_DEFAULT);
       intent.setAction(BS_PACKAGE + ".ENCODE");
       intent.putExtra("ENCODE_TYPE", "TEXT_TYPE");
       intent.putExtra("ENCODE_DATA", text);
       String targetAppPackage = findTargetAppPackage(intent);
       if (targetAppPackage == null) {
         showDownloadDialog();
       } else {
         intent.setPackage(targetAppPackage);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
         activity.startActivity(intent);
       }
        }
   
   }
