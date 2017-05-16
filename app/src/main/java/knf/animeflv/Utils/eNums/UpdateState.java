package knf.animeflv.Utils.eNums;

/**
 * Created by Jordy on 31/03/2016.
 */
public enum UpdateState {
    NO_UPDATE(0),
    DOWNLOADING(1),
    WAITING_TO_UPDATE(2),
    START_UPDATE_CHECK(3),
    FINISHED(4);
    int value;

    UpdateState(int value) {
        this.value = value;
    }
}
