package shouty;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.lang.reflect.Field;

import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertEquals;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterType;
import io.cucumber.datatable.*;
import io.cucumber.datatable.DataTable.TableConverter;

public class ShoutSteps {
    private static final String ARBITRARY_MESSAGE = "Hello, world";
    private final Shouty shouty = new Shouty();
    private Map<String, PeopleLocation> peopleLocs;

    public class DataTableConfigurer implements TypeRegistryConfigurer {

        @Override
        public Locale locale() {
            return Locale.ENGLISH;
        }

        @Override
        public void configureTypeRegistry(TypeRegistry registry) {
            registry.defineDataTableType(new DataTableType(PeopleLocation.class, new TableEntryTransformer<PeopleLocation>() {
                @Override
                public PeopleLocation transform(Map<String, String> entry) {
                    return new PeopleLocation(entry.get("name"), entry.get("x"), entry.get("y"));
                }
            }));
        }

    }

    @Given("^(.*) is at (\\d+),(\\d+)$")
    public void lucyIsAt(String shouter, int xCoord, int yCoord) throws Throwable {
        shouty.setLocation(shouter, new Coordinate(xCoord, yCoord));
    }

    @When("^(.*) shouts$")
    public void shouter_shouts(String shouter) {
        shouty.shout(shouter, ARBITRARY_MESSAGE);
    }

    @Then("Lucy should hear Sean")
    public void lucy_should_hear_sean() {
        assertEquals(1, shouty.getShoutsHeardBy("Lucy").size());
    }

    @Then("Lucy should hear nothing")
    public void lucy_should_hear_nothing() {
        assertEquals(emptyMap(), shouty.getShoutsHeardBy("Lucy"));
    }


    @And("^Lucy should not hear Oscar$")
    public void lucyShouldNotHearOscar() throws Throwable {
        assertEquals(false, shouty.getShoutsHeardBy("Lucy").containsKey("Oscar"));
    }

    @Then("^Lucy should not hear her own shout$")
    public void lucyShouldNotHearHerOwnShout() throws Throwable {
        assertEquals(false, shouty.getShoutsHeardBy("Lucy").containsKey("Lucy"));
    }

//    @Given("^people are located at$")
//    public void peopleAreLocatedAt(List<PeopleLocation> peopleLocation) throws Throwable {
//        for (PeopleLocation people : peopleLocation) {
////            String key = name.getName();
//            shouty.setLocation(people.getName(), new Coordinate(Integer.parseInt(people.getXCord()),Integer.parseInt(people.getYCord())));
//        }
//    }

    @Given("people are located at")
    public void peopleAreLocatedAt(DataTable dt) {
        List<List<String>> peopleLocation = dt.asLists(String.class);
        for(int i=1; i<peopleLocation.size(); i++) { //i starts from 1 because i=0 represents the header
            String strName = peopleLocation.get(i).get(0);
            Integer intX = Integer.parseInt(peopleLocation.get(i).get(1));
            Integer intY = Integer.parseInt(peopleLocation.get(i).get(2));
//            new PeopleLocation(strName,intX, intY);
            shouty.setLocation(strName, new Coordinate(intX,intY));
        }
    }

    @When("^they shout$")
    public void theyShout() throws Throwable {
        shouty.shout("Sean", ARBITRARY_MESSAGE);

    }

    @Then("^others should hear if they are within range$")
    public void othersShouldHearIfTheyAreWithinRange() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

}
