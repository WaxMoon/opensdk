package com.hack.opensdk;

/**
 * 基于安全考虑，将来CMD每一个版本都不能相同。
 * 所以将此java文件放在工程根目录的src/main下面，方便后续自动生成
 */
public class CmdConstants {

    public static int MODE_INSTALL_ALONE = 0x01 << 0;
    public static int MODE_FORCE_INSTALL = 0x01 << 1;

    public static final int CMD_APPLICATION_ATTACHBASE = 1;
    public static final int CMD_APPLICATION_ONCREATE = 2;

    public static final int CMD_INSTALL_PACKAGE = 10;
    public static final int CMD_UNINSTALL_PACKAGE = 11;
    public static final int CMD_UPDATA_PACKAGE = 12;
    public static final int CMD_REMOVE_PKG_DATA = 13;
    public static final int CMD_DELETE_PKG_CACHE = 14;

    public static final int CMD_GET_PACKAGE_INFO = 20;
    public static final int CMD_RESOLVE_INTENT = 21;
    public static final int CMD_QUERY_ACTIVITIES = 22;
    public static final int CMD_GET_ACTIVITY_INFO = 23;
    public static final int CMD_GET_INSTALLED_PKGS = 24;
    public static final int CMD_GET_PKG_SETTINGS = 25;
    public static final int CMD_GET_LAUNCHER_INTENT = 26;
    public static final int CMD_GET_UNAVAILABLE_PKGS = 27;

    public static final int CMD_START_PACKAGE = 30;
    public static final int CMD_START_ACTIVITY = 31;

    public static final int CMD_REGISTER_UNINSTALL_OBSERVER = 40;
    public static final int CMD_UNREGISTER_UNINSTALL_OBSERVER = 41;
    public static final int CMD_REGISTER_INSTALL_OBSERVER = 42;
    public static final int CMD_UNREGISTER_INSTALL_OBSERVER = 43;

    public static final int CMD_AGENT_JOB_SERVICE_BIND = 90;
    public static final int CMD_AGENT_JOB_SERVICE_UNBIND = 91;

    public static final int CMD_AGENT_PROVIDER_CALL = 100;

    public static final int CMD_AGENT_INTENT_SENDER = 120;

    public static final int CMD_CORE_PROVIDER_CREATE = 150;
    public static final int CMD_CORE_PROVIDER_CALL = 151;

    public static final int CMD_GET_ALL_USERID = 160;
    public static final int CMD_PKG_ALL_USERID = 161;

    public static final int CMD_GET_ATTRIBUTION_PARCEL = 200;
    public static final int CMD_BINDER_CALLING_UID = 201;

    public static final int CMD_ASSIST_PROVIDER_CALL = 210;

    public static final int CMD_ASSIST_ACTIVITY_CALL = 220;
    public static final int CMD_ASSIST_ACTIVITY_CALL2 = 221;

    public static final int CMD_FILE_PROVIDER_MAKE_URI = 240;

    public static final int CMD_GET_RUNTIME_PROPERTIES = 300;

    public static final int CMD_REGISTER_APPLICATION_CALLBACK = 400;

    // CMD_GET_PKG_SETTINGS Bundle key
    public static final String PKG_SET_REQUEST_ASSISTANT = "request_assistant";
    public static final String PKG_SUPPORTED_ABIS = "supported_abis";
    public static final String PKG_INSTALLED_MODE = "installed_mode";

    /**************BEGIN TransactProvider*******************/
    public static final String TRANSACT_PROVIDER_AUTHORITY = "com.hack.server.core.TransactProvider";
    public static final String TRANSACT_PROVIDER_METHOD = "transact";
    public static final String TRANSACT_KEY_CMD = "transact_cmd";

    public static final String TRANSACT_KEY_PKG = "pkg";
    public static final String TRANSACT_KEY_SHELL_PKG = "shell_pkg";
    public static final String TRANSACT_KEY_PROCESS = "agent_process";
    public static final String TRANSACT_KEY_SPACE = "space";
    public static final String TRANSACT_KEY_INTENT = "intent";
    public static final String TRANSACT_KEY_FROM_TOKEN = "from_token";

    public static final String TRANSACT_KEY_RESULT = "ret";
    public static final String TRANSACT_KEY_OUT_INTENT = "outIntent";

    public static final int TRANSACT_CMD_PROCESS_BINDED = 1;
    public static final int TRANSACT_CMD_OUTER_INTENT = 2;
    /**************END TransactProvider*******************/

    /**************BEGIN RUNTIME_PROPERTIES*******************/
    public static final String RUNTIME_HACK_CLASSLOADER = "hack.runtime.classloader";
    public static final String RUNTIME_PROPERTIES = "hack.runtime.properties";
    public static final String RUNTIME_PROPERTIES_SPACE = "space";
    public static final String RUNTIME_PROPERTIES_PKG = "pkg";
    public static final String RUNTIME_PROPERTIES_PROCESS = "process";
    public static final String RUNTIME_PROPERTIES_APPID = "appId";
    /**************BEGIN RUNTIME_PROPERTIES*******************/
}
