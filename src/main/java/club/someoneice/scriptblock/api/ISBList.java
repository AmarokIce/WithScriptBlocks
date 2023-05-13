package club.someoneice.scriptblock.api;

import club.someoneice.scriptblock.CacheObject;
import club.someoneice.scriptblock.DataSBapi;
import club.someoneice.scriptblock.SbInput;

import java.util.HashMap;
import java.util.List;

public interface ISBList {
    /**
     * Implement this interface and then register.<br />
     * 实现这个接口，然后注册它。<br />
     *
     * @param list The script in a list, here are all u want.<br />
     * 脚本参数都在这个列表，有一切你想要的。
     * @param pool The variable pool, get or set a variable in it. <br />
     * 变量池，设定或取出变量。
     * @param data Get player, world and block pos. <br />
     * 获取玩家，世界或块坐标的集成方案。
     *
     * @apiNote Create a new event and input it into the script's handler. <br />
     * When you get done all, register it to SbInput.putAct().
     * <br />
     * 创建一个新的事件加入脚本处理器。当你完成，在SbInput.putAct()中注册它。
     * @see SbInput
     * */
    void getList(List<Object> list, HashMap<String, CacheObject> pool, DataSBapi data);
}
