package cubex.mahesh.telephony_and9amfeb19

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.SmsManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    var uri:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendsms.setOnClickListener {
         /*   var sIntent = Intent(this@MainActivity,
                                                SendActivity::class.java)
            var dIntent = Intent(this@MainActivity,
                DeliverActivity::class.java)
            var spIntent = PendingIntent.getActivity(
          this@MainActivity,0,sIntent,0)
            var dpIntent = PendingIntent.getActivity(
                this@MainActivity,0,dIntent,0)
            var sManager = SmsManager.getDefault()
            sManager.sendTextMessage(mno.text.toString(),
              null,msg.text.toString(),
                spIntent,dpIntent) */
            var i = Intent()
            i.setAction(Intent.ACTION_SEND)
            i.putExtra(Intent.EXTRA_TEXT , msg.text.toString())
            i.setType("*/*")
            startActivity(i)
        }
        makecall.setOnClickListener {
            var i = Intent()
            i.setAction(Intent.ACTION_CALL)
            i.setData(Uri.parse("tel:"+mno.text.toString()))
           startActivity(i)
        }

        attach.setOnClickListener {

    var aDialog = AlertDialog.Builder(this@MainActivity)
    aDialog.setTitle("Message")
    aDialog.setMessage("Take a photo/select a file for adding an attachment")
    aDialog.setPositiveButton("Camera",
        DialogInterface.OnClickListener { dialogInterface, i ->
            var i = Intent( )
            i.setAction("android.media.action.IMAGE_CAPTURE")
            startActivityForResult(i,123)
        })
    aDialog.setNegativeButton("File Explorer",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    var i = Intent( )
                    i.setAction(Intent.ACTION_GET_CONTENT)
                    i.setType("*/*")
                    startActivityForResult(i,124)
                })
    aDialog.setNeutralButton("Cancel",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
    aDialog.show()
        }
        sendmail.setOnClickListener {
            var i = Intent()
            i.setAction(Intent.ACTION_SEND)
            i.putExtra(Intent.EXTRA_EMAIL,
                arrayOf(email.text.toString()))
            i.putExtra(Intent.EXTRA_SUBJECT, subject.text.toString())
            i.putExtra(Intent.EXTRA_TEXT, message.text.toString())
            i.putExtra(Intent.EXTRA_STREAM,uri)
            i.setType("message/rfc822")
            startActivity(i)
        }
        javamail.setOnClickListener {
            var lop = LongOperation(email.text.toString(),
                subject.text.toString(),
                message.text.toString())
            lop.execute()
        }

    }  // onCreate( )

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

   if(requestCode == 123 && resultCode == Activity.RESULT_OK)
   {
            var bmp = data?.extras?.get("data") as Bitmap
            uri = getImageUri(this@MainActivity, bmp)
   }else  if(requestCode == 124 && resultCode == Activity.RESULT_OK)
   {
        uri = data?.data
   }

    }  // onActivityResult( )

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }


} //MainActivity
