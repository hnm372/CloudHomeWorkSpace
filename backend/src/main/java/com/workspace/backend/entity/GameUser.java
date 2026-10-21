package com.workspace.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "game_user")
public class GameUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_uuid")
    private String userUuid;

    @TableField(value = "combat_capacity")
    private Double combatCapacity;

    private Integer level;

    @TableField(value = "empirical_value")
    private Double empiricalValue; // 经验值

    private Double blood;

    @TableField(value = "physical_attack")
    private Double physicalAttack;

    @TableField(value = "magic_attak")
    private Double magicAttack;

    @TableField(value = "physical_defend")
    private Double physicalDefend;

    @TableField(value = "magic_defend")
    private Double magicDefend;

    private Double tenacity;

    @TableField(value = "auto_recovery")
    private Double autoRecovery;

    private Integer feeding;

    @TableField(value = "kill_count")
    private Double killCount;

    private Integer checkpoint;

    @TableField(value = "equipments_head_uuid")
    private String equipmentsHeadUuid;

    @TableField(value = "equipments_body_uuid")
    private String equipmentsBodyUuid;

    @TableField(value = "equipments_foot_uuid")
    private String equipmentsFootUuid;

    @TableField(value = "equipments_weapon_uuid")
    private String equipmentsWeaponUuid;

    @TableField(value = "equipments_broach_uuid")
    private String equipmentsBroachUuid;

    private Integer state;

}
