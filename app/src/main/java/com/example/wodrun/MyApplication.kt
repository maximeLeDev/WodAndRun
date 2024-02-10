import java.util.Calendar

class GlobalVariables {
    companion object {
        val calendar = Calendar.getInstance()
        //+1 sur le mois car tableau qui commence à 0 fini à 11
        val date = "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH)+1}-${calendar.get(Calendar.YEAR)}"
    }
}