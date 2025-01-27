package eni.tp.encheres.bo;

public class Retrait {
    private String rue;
    private long code_postal;
    private String ville;

    public Retrait(String rue, long code_postal, String ville) {
        super();
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;

    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public long getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(long code_postal) {
        this.code_postal = code_postal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

}
