package common;

public class SurfaceLocation implements Location {

    private final String name;

    public SurfaceLocation() {
        this.name = "Martian Surface";
    }

    @Override
    public String getName() {
        return name;
    }
}
