package sample.marcos.testefcm.model;

/**
 * Created by marcosvinicius on 14/05/2018.
 */

public class Computador {
    private String ID;
    private String nomePc;
    private String nomeIp;
public Computador(){

}
    public Computador(String ID, String nomePc, String nomeIp) {
        this.ID= ID;
        this.nomePc= nomePc;
        this.nomeIp= nomeIp;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNomePc() {
        return nomePc;
    }

    public void setNomePc(String nomePc) {
        this.nomePc = nomePc;
    }

    public String getNomeIp() {
        return nomeIp;
    }

    public void setNomeIp(String nomeIp) {
        this.nomeIp = nomeIp;
    }

    @Override
    public String toString() {
        return nomePc;
    }
}
