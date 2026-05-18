package testingTheExample

import munit.FunSuite
import exampleSolution.{Property, Resource, ResourceKind, ResourceMap, findNext}

class ExampleTest  extends FunSuite {

  /*test("testing the findNext method it's content"){

    // Example usage
    val resource = Resource(79, 92, ResourceKind.Seed)
    val property = Seq(Property(50, 98, 2), Property(52, 50, 48))
    val map = ResourceMap(ResourceKind.Seed, ResourceKind.Soil, property)

    val ResourceMap(_, to, properties) = map
    println(s"value to is $to")
    println(s"value propeties is $properties")

    val (newResources, explore) =
      val initial = (Seq.empty[Resource], Option(resource))
      properties.foldLeft(initial) {
        case ((acc, Some(explore)), prop) =>
          println(s"explore is : $explore")
          val Resource(start, end, _) = explore
          val propStart = prop.sourceStart
          val propEnd = prop.sourceEnd
          println(s"popStar is ${propStart}")
          println(s"popStar is ${propEnd}")
          val underRange = Option.when(start < propStart) {
            println(s"Resource starts before property range: creating resource from $start to ${Math.min(propStart - 1, end)}")
            Resource(start, Math.min(propStart - 1, end), to)
          }
          println(s"what's an option whenm the underRange: ${underRange}")
          

      }


  }
*/
}
