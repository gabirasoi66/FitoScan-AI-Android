package com.fitoscan.ai
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
object IrrigationScreen {
 fun show(a:AppCompatActivity,back:()->Unit){
  val r=FitoUi.page(a,"Irigații, Apă & Energie")
  FitoUi.card(a,r,"💧 Proiectarea sistemului","Cultură → suprafață → sursă de apă → debit → tip de irigare → pompă → filtrare → conducte → energie.")
  FitoUi.card(a,r,"🌱 Tipuri de irigare","Picurare, aspersie și microaspersie, adaptate culturii și condițiilor locale.")
  FitoUi.card(a,r,"⚡ Alimentare electrică","Rețea, generator sau sistem fotovoltaic. Dimensionarea necesită date reale.")
  FitoUi.card(a,r,"🧮 Calcule","Debitele, consumul și timpii de udare vor fi calculate din datele introduse.")
  FitoUi.button(a,r,"← Înapoi"){back()}
  FitoUi.show(a,r)
 }
}
