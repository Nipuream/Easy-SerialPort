package com.nipuream.library.fetcher.control;


import com.nipuream.library.common.WriterInfo;

/**
 * Created by yanghui11 on 2020/4/11.
 */

public interface IController {

      void retransControl(WriterInfo info);

       WriterInfo decWriterInfo(int deviceType, int commId);

}
