package kotleni.b0mb3r.services;

public class Sravni extends Gorparkovka {

    public Sravni() {
        setUrl("https://mobile.sravni.ru/v1/auth");
        setMethod(POST);
    }
}
