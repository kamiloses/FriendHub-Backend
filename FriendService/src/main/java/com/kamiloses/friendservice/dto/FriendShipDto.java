package com.kamiloses.friendservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class FriendShipDto {

    private String userIdOrFriendId;
    private String chatId;


}



