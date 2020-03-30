package org.example.constant;

public class WechatUrlConstant {

    public static final String GET_ACCESS_TOKEN="https://api.weixin.qq.com/cgi-bin/token";
    public static final String GET_TICKET="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

    public static final String GET_API_IP="https://api.weixin.qq.com/cgi-bin/get_api_domain_ip";

    //menu
    public static final String CREATE_MENU="https://api.weixin.qq.com/cgi-bin/menu/create";
    public static final String SEARCH_MENU="https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info";

    //kf
    public static final String CREATE_KF="https://api.weixin.qq.com/customservice/kfaccount/add";
    public static final String SEND_MESSAGE="https://api.weixin.qq.com/cgi-bin/message/custom/send";

    //上传图片
    public static final String UPLOADING_PICTURE="https://api.weixin.qq.com/cgi-bin/media/uploadimg";
    public static final String SEND_TEMPLATE="https://api.weixin.qq.com/cgi-bin/message/template/send";

}
