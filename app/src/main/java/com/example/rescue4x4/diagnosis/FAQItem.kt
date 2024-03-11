@file:Suppress("SpellCheckingInspection") // da suppress la verificarea de typo

package com.example.rescue4x4.diagnosis

import android.content.res.Resources
import com.example.rescue4x4.MainActivity
import com.example.rescue4x4.R

data class FAQItem(
    val question: String,
    val answer: String
)

val faqList = listOf(
    FAQItem("De ce se aude un zgomot puternic de la trenul de rulare?", "Zgomotul puternic poate fi cauzat de lovituri sau daune la componentele trenului de rulare, cum ar fi articulațiile cu sferă, rulmenții sau barele stabilizatoare."),
    FAQItem("De ce se simte o vibrație puternică la viteze mari?", "Vibrația puternică poate fi rezultatul dezechilibrului roților, deteriorării roților sau a suspensiilor, sau a uzurii articulațiilor sau rulmenților."),
    FAQItem("De ce se aprinde lumina de avertizare a motorului?", "Aprinderea lumii de avertizare a motorului poate indica o gamă largă de probleme, inclusiv defecțiuni ale sistemului de alimentare cu combustibil, probleme la sistemul de aprindere sau emisii excesive de gaze de eșapament."),
    FAQItem("Ce trebuie să fac dacă scade presiunea uleiului?", "Scăderea presiunii uleiului poate indica o scurgere de ulei, uzura sau blocarea filtrelor de ulei, sau un nivel scăzut de ulei. Este esențial să opriți imediat motorul și să verificați nivelul și calitatea uleiului."),
    FAQItem("De ce nu mai funcționează sistemul de direcție a mașinii?", "Defectele în sistemul de direcție pot fi cauzate de pierderea lichidului de direcție asistată, uzura sau deteriorarea pompei de direcție asistată, sau probleme cu barele de direcție sau articulațiile direcției."),
    FAQItem("De ce se simte mașina că pierde puterea la accelerare?", "Pierderea puterii la accelerare poate fi cauzată de mai multe probleme, inclusiv un filtru de aer murdar, o presiune scăzută a combustibilului, injecție incorectă de combustibil sau o defecțiune a sistemului de aprindere."),
    FAQItem("Ce provoacă fisuri sau deteriorări ale carcasei motorului?", "Fisurile sau deteriorările carcasei motorului pot fi cauzate de supraîncălzirea motorului, presiunea crescută în cilindri sau defecte în blocul motor, cum ar fi fisuri de stres sau defecte de turnare."),
    FAQItem("De ce se aud bătăi sau zgomote metalice sub mașină?", "Bătăile sau zgomotele metalice pot fi cauzate de componente slabe ale suspensiei, precum bară stabilizatoare ruptă sau articulații cu sferă uzate, sau de obiecte străine care bat de partea de jos a mașinii."),
    FAQItem("Ce trebuie să fac dacă se aprinde lumina de avertizare a sistemului de frânare?", "Aprinderea lumii de avertizare a sistemului de frânare poate indica uzura plăcuțelor de frână, scurgerea lichidului de frână, defecte în sistemul de frânare sau blocarea etrierelor de frână."),
    FAQItem("De ce se simte o miros urât sau de fum din compartimentul motor?", "Mirosul urât sau de fum din compartimentul motor poate indica o scurgere de lichid de răcire, un ulei ars, sau chiar un incendiu în compartimentul motor. Este esențial să opriți imediat motorul și să investigați cauza."),
    FAQItem("Ce cauzează o cădere bruscă a nivelului lichidului de răcire?", "Căderea bruscă a nivelului lichidului de răcire poate fi cauzată de o scurgere de lichid de răcire din radiator, furtunuri de răcire sparte, sau defecte ale pompei de apă."),
    FAQItem("De ce se aprinde lumina de avertizare a airbag-urilor?", "Aprinderea lumii de avertizare a airbag-urilor poate indica o problemă cu sistemul de airbaguri, inclusiv senzori defectuoși, cabluri rupte sau module de control defecte."),
    FAQItem("Ce trebuie să fac dacă se aprinde lumina de avertizare a presiunii uleiului?", "Aprinderea lumii de avertizare a presiunii uleiului poate indica o scădere a presiunii uleiului în motor, ceea ce poate fi cauzată de o scurgere de ulei, uzura sau blocarea filtrelor de ulei, sau o pompă de ulei defectă."),
    FAQItem("De ce se aud sunete ciudate la schimbarea treptelor de viteză?", "Sunetele ciudate la schimbarea treptelor de viteză pot fi cauzate de ambreiaj uzat, sincronizatoare defecte sau ulei de transmisie contaminat."),
    FAQItem("Ce trebuie să fac dacă se simte o miros urât sau de fum în habitaclu?", "Mirosul urât sau de fum în habitaclu poate fi cauzat de o scurgere de lichid de răcire, un sistem de încălzire sau de aer condiționat defect, sau obiecte străine arse în sistemul de ventilație."),
    FAQItem("De ce se aprinde lumina de avertizare a ABS-ului?", "Aprinderea lumii de avertizare a ABS-ului poate indica probleme cu sistemul de frânare antiblocare, inclusiv senzori de roți defectuoși, cabluri rupte sau module de control ABS defecte."),
    FAQItem("Ce cauzează o scădere a presiunii uleiului la viteze mari?", "Scăderea presiunii uleiului la viteze mari poate fi cauzată de o scurgere de ulei la garniturile supapei, de uzura sau blocarea filtrelor de ulei, sau de o pompă de ulei defectă."),
    FAQItem("De ce se aprinde lumina de avertizare a controlului tracțiunii?", "Aprinderea lumii de avertizare a controlului tracțiunii poate indica probleme cu sistemul de control al tracțiunii, inclusiv senzori de roți defectuoși, cabluri rupte sau module de control tracțiune defecte."),
    FAQItem("Ce trebuie să fac dacă mașina este greu de pornit dimineața?", "Dificultățile la pornirea mașinii dimineața pot fi cauzate de o baterie descărcată, un demaror uzat sau injectoare de combustibil înfundate."),
    FAQItem("De ce se aprinde lumina de avertizare a sistemului de monitorizare a presiunii pneurilor?", "Aprinderea lumii de avertizare a sistemului de monitorizare a presiunii pneurilor poate indica presiunea pneurilor sub sau peste limita recomandată, sau probleme cu senzorii de presiune a pneurilor.")
)
//filtering
