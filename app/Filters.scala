/**
  * Created by anil.mathew on 11/9/2016.
  */
import javax.inject.Inject

import play.api.http.HttpFilters
import play.filters.cors.CORSFilter

class Filters @Inject()(cors: CORSFilter) extends HttpFilters {

    val filters = Seq(cors)
}
