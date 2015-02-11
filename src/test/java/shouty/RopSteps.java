package shouty;

import cucumber.api.java.Before;
import cucumber.api.java.no.Gitt;
import cucumber.api.java.no.Når;
import cucumber.api.java.no.Så;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class RopSteps {

    private Person sonja;
    private Person harald;
    private String sonjasBeskjed;
    private Postbud postbud;

    @Before
    public void lagPostbud() {
        postbud = new Postbud();
    }

    @Gitt("^at Sonja er på Slottet$")
    public void at_Sonja_er_på_Slottet() throws Throwable {
        sonja = new Person(postbud);
        double[] slottet = {59.917043, 10.727377};
        sonja.erPå(slottet);
    }

    @Gitt("^Harald er på Munch-muséet$")
    public void harald_er_på_Munch_muséet() throws Throwable {
        harald = new Person(postbud);
        double[] munchMuseet = {59.916951, 10.77498};
        harald.erPå(munchMuseet);
    }

    @Gitt("^Harald er på Egertorget$")
    public void harald_er_på_Egertorget() throws Throwable {
        harald = new Person(postbud);
        double[] egertorget = {59.9128017,10.7418443};
        harald.erPå(egertorget);
    }

    @Når("^Sonja roper \"(.*?)\"$")
    public void sonja_roper(String beskjed) throws Throwable {
        sonja.roper(beskjed);
        sonjasBeskjed = beskjed;
    }

    @Så("^hører ikke Harald meldingen \"(.*?)\"$")
    public void hører_ikke_Harald_meldingen(String forventetMelding) throws Throwable {
        List<String> forventeteMeldinger = asList(forventetMelding);
        assertNotEquals(forventeteMeldinger, harald.mottatteMeldinger());
    }

    @Så("^hører Harald meldingen \"(.*?)\"$")
    public void hører_Harald_meldingen(String forventetMelding) throws Throwable {
        List<String> forventeteMeldinger = asList(forventetMelding);
        assertEquals(forventeteMeldinger, harald.mottatteMeldinger());
    }
}
