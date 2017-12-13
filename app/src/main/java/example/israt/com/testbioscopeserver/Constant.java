package example.israt.com.testbioscopeserver;


public class Constant {
    public static class Database {
        public static final String DATABASE_NAME = "bioscope-test";
        public static final int DATABASE_VERSION = 1;
        public static final String TABLESTATUS = "server_status";


        public static class TABLE_STATUS {
            public static final String ID = "id";
            public static final String DATE = "date";//
            public static final String DOWN_TIME = "downtime";
            public static final String UP_TIME = "uptime";
            public static final String SERVER_STATUS = "status";

        }

    }

}