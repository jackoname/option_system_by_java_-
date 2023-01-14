package pojo;

public class PCB {

    int id;//id
    String state,ismer;//状态
    Process process;//程序xinxi
    Program[] programs;//程序大小
    Run []runs;//程序运行过程
    int endtime,runtime;
    int nextdo;
    int lefttime;
    boolean[] isnopage=new boolean[5];
    boolean[] isnopageLru=new boolean[5];
    int pag;
    public boolean[] getIsnopageLru() {
        return isnopageLru;
    }

    public int getPag() {
        return pag;
    }

    public void setPag(int pag) {
        this.pag = pag;
    }

    public void setIsnopageLru(boolean[] isnopageLru) {
        this.isnopageLru = isnopageLru;
    }

    public boolean[] getIsnopage() {
        return isnopage;
    }

    public void setIsnopage(boolean[] isnopage) {
        this.isnopage = isnopage;
    }

    public int getLefttime() {
        return lefttime;
    }

    public void setLefttime(int lefttime) {
        this.lefttime = lefttime;
    }

    public int getNextdo() {
        return nextdo;
    }

    public void setNextdo(int nextdo) {
        this.nextdo = nextdo;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getEndtime() {
        return endtime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    public PCB(){
        programs=new Program[6];
        runs =new Run[6];
        process =new Process();
        for (int i = 0; i < 6; i++) {
            programs[i]=new Program();
            runs[i]=new Run();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsmer() {
        return ismer;
    }

    public void setIsmer(String ismer) {
        this.ismer = ismer;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Program[] getPrograms() {
        return programs;
    }

    public void setPrograms(Program[] programs) {
        this.programs = programs;
    }

    public Run[] getRuns() {
        return runs;
    }

    public void setRuns(Run[] runs) {
        this.runs = runs;
    }
}
