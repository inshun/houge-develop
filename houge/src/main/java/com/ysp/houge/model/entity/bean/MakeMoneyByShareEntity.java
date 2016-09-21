package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * 描述： 分享赚钱实体
 *
 * @ClassName: MakeMoneyByShareEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月12日 下午3:33:15
 * 
 *        版本: 1.0
 */
public class MakeMoneyByShareEntity implements Serializable {
	private String invite_id;
	private String download_url;
	private String share_url;

	public MakeMoneyByShareEntity() {
		super();
	}

    public MakeMoneyByShareEntity(String invite_id, String download_url, String share_url) {
        this.invite_id = invite_id;
        this.download_url = download_url;
        this.share_url = share_url;
    }

    public String getInvite_id() {
		return invite_id;
	}

	public void setInvite_id(String invite_id) {
		this.invite_id = invite_id;
	}

	public String getDownload_url() {
		return download_url;
	}

	public void setDownload_url(String download_url) {
		this.download_url = download_url;
	}

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }
}
