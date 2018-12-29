package stream.xlinuxtools.lib;

public class FilesystemClass {

    private boolean isDirectory;
    private String name;
    private String path;

    public FilesystemClass(){
    }

    public FilesystemClass(boolean isDirectory, String name, String path){
        this.isDirectory = isDirectory;
        this.name = name;
        this.path = path;

    }

    public String getFullPath(){
        return this.path + "/" + this.name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPath(){
        return this.path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public boolean isDirectory() {
        return this.isDirectory;
    }

    public void setIsDirectory(boolean isDirectory){
        this.isDirectory = isDirectory;
    }
}
