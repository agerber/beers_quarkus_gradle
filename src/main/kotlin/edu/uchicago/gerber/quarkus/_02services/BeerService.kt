package edu.uchicago.gerber.quarkus._02services



import edu.uchicago.gerber.quarkus._03repositories.BeerRepoInterface
import edu.uchicago.gerber.quarkus._03repositories.MongoBeerRepository
import edu.uchicago.gerber.quarkus._03repositories.SomeBeerRepository
import edu.uchicago.gerber.quarkus._04models.Beer
import io.quarkus.mongodb.panache.kotlin.PanacheQuery
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class BeerService  {

    @Inject
    @ConfigProperty(name = "repository.type")
    private lateinit var repositoryType: String

    //contains the instantiated object stored an interface reference
    private lateinit var interfaceRepository: BeerRepoInterface

    @PostConstruct
    fun initialize(){
        interfaceRepository = when (repositoryType) {
            "mongo" -> MongoBeerRepository()
            "some" -> SomeBeerRepository()
            else -> SomeBeerRepository()
        }
    }
    fun create(beer: Beer){
        interfaceRepository._create(beer)
    }
    fun create(beers: List<Beer>){
        interfaceRepository._create(beers)
    }
    fun readById(id:String): Beer{
      return  interfaceRepository._readById(id)
    }
    fun readAll(): List<Beer>{
        return  interfaceRepository._readAll()
    }
    fun update(updatedBeer: Beer){
        return  interfaceRepository._update(updatedBeer)
    }
    fun deleteById(id:String){
        return  interfaceRepository._deleteById(id)
    }
    fun deleteAll(){
        interfaceRepository._deleteAll()
    }
    fun count() : Long{
       return interfaceRepository._count()
    }
    fun findAll(): PanacheQuery<Beer>?{
        return interfaceRepository._findAll()

    }
//    @PreDestroy
//    fun purgeDatabase(){
//        //WARNING - DO NOT INCLUDE THIS IN PRODUCTION
//        interfaceRepository._deleteAll()
//    }



}
