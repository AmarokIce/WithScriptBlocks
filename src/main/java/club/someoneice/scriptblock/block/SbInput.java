package club.someoneice.scriptblock.block;

import club.someoneice.scriptblock.block.api.ISBList;
import com.google.common.collect.Maps;

import java.util.Map;

public class SbInput {
    /**
     * We have a method for input the event right buddy? So lets using the method for push your event and don't touch this map. :)
     * */
    static final Map<String, ISBList> INPUT_MAP = Maps.newHashMap();

    /**
     * Create a new event and push into the ScB Handle. <br />
     * 创建一个新的事件并推入ScB的处理器。
     *
     * @param name The name for key, best to mark with '@' if you like. <br />
     *            事件的名称,如果你愿意的话最好使用“@”标记。
     * @param act The processor, implements ISBList and override getList(), the list will have all you want. <br />
     *           事件处理器，接入接口 ISBList 并Override getList() 方法，列表中会有你想要的一切。
     * @see ISBList
     * */
    public static void putAct(String name, ISBList act) {
        INPUT_MAP.put(name, act);
    }
}
