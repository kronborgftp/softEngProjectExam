/**
 *
 *
 * @author Frederik
 */
package model;

public class FixedActivity {
    private final String id;
    private final String name;

    public FixedActivity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
