import java.util.Calendar

class GlobalVariables {
    companion object {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Les mois commencent à 0
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val date = "$day-$month-$year"
        var myVariable: String = date
    }
}