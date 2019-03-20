package sample.Chat;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class UniqueID {

    private static AtomicInteger idUser = new AtomicInteger();

    public static UUID getUUID() {
        return UUID.randomUUID();
    }

    public static int getUniqueInt() {
        return idUser.getAndIncrement();
    }
}
