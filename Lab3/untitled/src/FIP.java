public class FIP {
    private final String atom;
    private final int codeAtom;
    private final int tsCode;

    public FIP(String atom, int codeAtom, int tsCode) {
        this.atom = atom;
        this.codeAtom = codeAtom;
        this.tsCode = tsCode;
    }

    @Override
    public String toString() {
        if(tsCode == -1)
            return "Simbol: " + atom + "\t<->\tCode: " + codeAtom;
        else
            return "Simbol: " + atom + "\t<->\tCode: " + codeAtom + "\t<->\tTS Code: " + tsCode;
    }
}