package it.unitn.science.lpsmt.uotnod.plugins.family;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import it.unitn.science.lpsmt.uotnod.UotnodXMLParser;
import it.unitn.science.lpsmt.uotnod.plugins.*;

public class FamilyActParser extends UotnodXMLParser {

	FamilyActParser(){
		super.tagName = "TAB_Attività";	
	}	
	
	// Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
	// to their respective "read" methods for processing. Otherwise, skips the tag.
	protected FamilyAct readActivity(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, this.tagName);
		
		long actId = -1;
		long actOrgId = -1;
		String actName = null;
		String actType = null;
		String actDesc = null;
		String actDateStart = null; // Date type
		String actDateEnd = null; // Date type
		String actFreq = null;
		String actTimes = null;
		String actDays = null;
		String actAddress = null;
		String actTypeDR = null;
		String actPriveType = null;
		String actPrice = null;
		String actAge = null;
		String actAgeNotes = null;
		String actVinRes = null;
		String actRef = null;
		String actReg = null;
		Boolean actFamilyCert = false;
		String actInfoLink = null;
		//Comuni
		//Mese
		
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			
			String item = parser.getName();

			if (item.equals("Id_attività")){
				actId = Integer.parseInt(getValue(parser,"Id_attività"));
			}
			else if (item.equals("ID_racc_org")){
				actOrgId =  Integer.parseInt(getValue(parser,"ID_racc_org"));
			}
			else if (item.equals("Nome_x0020_attività")){
				actName = getValue(parser,"Nome_x0020_attività");
			}
			else if (item.equals("Tipo_x0020_attività")){
				actType = getValue(parser,"Tipo_x0020_attività");
			}
			else if (item.equals("Descrizione_x0020_sintetica_x0020_dell_x0027_attività")){
				actDesc = getValue(parser,"Descrizione_x0020_sintetica_x0020_dell_x0027_attività");
			}
			else if (item.equals("Data_x0020_inizio_x0020_periodo_x0020_attività")){
				actDateStart = getValue(parser,"Data_x0020_inizio_x0020_periodo_x0020_attività");
			}
			else if (item.equals("Data_x0020_fine_x0020_periodo_x0020_attività")){
				actDateEnd = getValue(parser,"Data_x0020_fine_x0020_periodo_x0020_attività");
			}
			else if (item.equals("Frequenza")){
				actFreq = getValue(parser,"Frequenza");
			}
			else if (item.equals("Orari_x0020__x002F__x0020_periodicità_x002F__x0020_turni_x0020_di_x0020_svolgimento")){
				actTimes = getValue(parser,"Orari_x0020__x002F__x0020_periodicità_x002F__x0020_turni_x0020_di_x0020_svolgimento");
			}
			else if (item.equals("L_x0027_attività_x0020_si_x0020_svolge_x0020_nei_x0020_seguenti_x0020_giorni")){
				actDays = getValue(parser,"L_x0027_attività_x0020_si_x0020_svolge_x0020_nei_x0020_seguenti_x0020_giorni");
			}
			else if (item.equals("Sede_x002F_i_x0020_presso_x0020_cui_x0020_si_x0020_svolgerà_x0020_l_x0027_attività")){
				actAddress = getValue(parser,"Sede_x002F_i_x0020_presso_x0020_cui_x0020_si_x0020_svolgerà_x0020_l_x0027_attività");
			}
			else if (item.equals("Tipo_x0020_di_x0020_attività_x0020__x0028_diurna_x002F_residenziale_x0029_")){
				actTypeDR = getValue(parser,"Tipo_x0020_di_x0020_attività_x0020__x0028_diurna_x002F_residenziale_x0029_");
			}
			else if (item.equals("Tipo_x0020_di_x0020_costo")){
				actPriveType = getValue(parser,"Tipo_x0020_di_x0020_costo");
			}
			else if (item.equals("Specifiche_x0020_sul_x0020_costo")){
				actPrice = getValue(parser,"Specifiche_x0020_sul_x0020_costo");
			}
			else if (item.equals("Fascia_x002F_e_x0020_di_x0020_età_x0020_destinatari")) {
				actAge = getValue(parser,"Fascia_x002F_e_x0020_di_x0020_età_x0020_destinatari");
			}
			else if (item.equals("Note_x0020_su_x0020_età_x0020_dei_x0020_destinatari")){
				actAgeNotes = getValue(parser,"Note_x0020_su_x0020_età_x0020_dei_x0020_destinatari");
			}
			else if (item.equals("Vincoli_x0020_di_x0020_residenza_x0020_dei_x0020_destinatari")){
				actVinRes = getValue(parser,"Vincoli_x0020_di_x0020_residenza_x0020_dei_x0020_destinatari");
			}
			else if (item.equals("Referenti_x0020_che_x0020_possono_x0020_fornire_x0020_informazioni_x0020_su_x0020_questa_x0020_attività")){
				actRef = getValue(parser,"Referenti_x0020_che_x0020_possono_x0020_fornire_x0020_informazioni_x0020_su_x0020_questa_x0020_attività");
			}
			else if (item.equals("Modalità_x0020_e_x0020_tempi_x0020_di_x0020_iscrizione")){
				actReg = getValue(parser,"Modalità_x0020_e_x0020_tempi_x0020_di_x0020_iscrizione");
			}
			else if (item.equals("L_x0027_attività_x0020_è_x0020_marchiata_x0020__x0022_Family_x0020_in_x0020_Trentino_x0022__x003F_")){
				actFamilyCert = getValue(parser,"L_x0027_attività_x0020_è_x0020_marchiata_x0020__x0022_Family_x0020_in_x0020_Trentino_x0022__x003F_").equalsIgnoreCase("si");
			}
			else if (item.equals("Scheda")){
				actInfoLink = getValue(parser,"Scheda");
			} 
			else  {			
				skip(parser);
			}			
		}
		FamilyAct myAct = new FamilyAct(actId, actOrgId, actName, actType, actDesc, actDateStart, actDateEnd, actFreq, actTimes, actDays, actAddress, actTypeDR, actPriveType, actPrice, actAge, actAgeNotes, actVinRes, actRef, actReg, actFamilyCert, actInfoLink);					
		return myAct; 
	}

	@Override
	protected Entry readEntry(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
}