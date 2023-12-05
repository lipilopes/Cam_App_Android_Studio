package com.example.camera

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity()
{
    private lateinit var photoImageView: ImageView // Declaração de uma ImageView para exibir a foto
    private val REQUEST_IMAGE_CAPTURE = 1 // Constante usada para identificar a solicitação de captura de imagem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Define o layout da atividade

        // Inicializa a ImageView e o botão da interface do usuário usando seus IDs definidos no XML
        photoImageView = findViewById(R.id.photoImageView)
        val takePhotoButton: Button = findViewById(R.id.takePhotoButton)

        // Configura um ouvinte de clique para o botão
        takePhotoButton.setOnClickListener {
            dispatchTakePictureIntent() // Quando o botão é clicado, chama a função para tirar uma foto
        }
    }

    private fun dispatchTakePictureIntent() {
        // Cria uma intenção para abrir a câmera do dispositivo
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Verifica se existe uma atividade de câmera para lidar com a intenção
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            // Inicia a atividade da câmera e aguarda o resultado
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Manipula o resultado da atividade da câmera
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Se a foto foi tirada com sucesso (RESULT_OK), continue
            val imageBitmap = data?.extras?.get("data") as Bitmap
            photoImageView.setImageBitmap(imageBitmap)
            // Exibe a foto capturada na ImageView

            // Salva a imagem na galeria
            MediaStore.Images.Media.insertImage(
                contentResolver,
                imageBitmap,
                "Title",
                "Description"
            )
        }
    }
}