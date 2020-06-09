package main.controller.operations.searchOperation.criterias;

import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOValue;

public abstract class Criteria extends IOObject {

    public Criteria() {
    } //*/

    public Criteria(IOObject object) {
        this.put("criteria", object);
    }

    public abstract IOValue apply();
}
