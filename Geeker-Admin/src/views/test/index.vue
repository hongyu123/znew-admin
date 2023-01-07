<template>
  <div class="table-box">
    <button @click="submit">提交</button>
    <Monaco v-model="text" :readOnly="true"></Monaco>
  </div>
</template>
<script lang="ts" setup>
import Monaco from "@/components/editor/Monaco.vue";
import { ref } from "vue";

const submit = () => {
  alert(text.value);
};
const text = ref(
  `
package com.hfw.model.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.hfw.common.entity.BaseEntity;
import com.hfw.common.enums.EnableState;
import com.hfw.common.enums.Gender;
import com.hfw.common.support.jackson.ApiResult;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Table;

/**
* app用户
* @author zyh
* @date 2022-11-25
*/
@Data
@Accessors(chain = true)
@Table(name = "app_user")
@JsonFilter(ApiResult.includeFilter)
public class AppUser extends BaseEntity {
    /** id **/
    private Long id;

    /** 昵称 **/
    private String nickname;

    /** 头像 **/
    private String avatar;

    /** 手机号码 **/
    private String phone;

    /** 性别 **/
    private Gender gender;
    public String getGenderDesc(){
        return this.gender==null ?"":this.gender.getDesc();
    }

    /** 生日 **/
    private java.time.LocalDate birth;

    /** 地址 **/
    private String address;

    /** 详细地址 **/
    private String addressDetail;

    /** 标签(逗号分隔) **/
    private String tags;

    /** 关注数量 **/
    private Integer focusNum;

    /** 粉丝数量 **/
    private Integer fansNum;

    /** 积分 **/
    private Integer integral;

    /** 作品数量 **/
    private Integer worksNum;


    /** 启用状态 **/
    private EnableState enableFlag;


    /** 微信openid **/
    private String openid;

    /** apple_id **/
    private String appleId;

    /** im_id **/
    private String imId;

    private String comment;
}
`
);
</script>
