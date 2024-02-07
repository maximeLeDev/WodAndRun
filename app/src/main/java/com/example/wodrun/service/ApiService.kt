import com.example.wodrun.model.Exo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
interface ApiService {
    @GET("wod.json")
    suspend fun getExos(): Response<MutableList<Exo>>
}