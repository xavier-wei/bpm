import {Cpape05mModel} from "@/shared/model/bpm/cpape05mModel";

export interface IUserModel {
    acntIsValid: boolean;
    cpape05m?: Cpape05mModel[] | null;
    createTimestamp ?: string | null;
    createUserId?: string | null;
    deptId ?: string | null;
    email ?: string | null;
    empId ?: string | null;
    fromHr?: string | null;
    lastLoginDate ?: string | null;
    lastLoginIp ?: string | null;
    ldapId?: string | null;
    lineToken ?: string | null;
    modifyTimestamp ?: string | null;
    modifyUserId ?: string | null;
    orgId ?: string | null;
    tel1 ?: string | null;
    tel2 ?: string | null;
    titleId ?: string | null;
    userId ?: string | null;
    userName ?: string | null;
    userEName ?: string | null;
}


export class UserModel implements IUserModel {
    constructor(
       public acntIsValid: boolean,
       public cpape05m?: Cpape05mModel[] | null,
       public createTimestamp ?: string | null,
       public createUserId?: string | null,
       public deptId ?: string | null,
       public email ?: string | null,
       public empId ?: string | null,
       public fromHr?: string | null,
       public lastLoginDate ?: string | null,
       public lastLoginIp ?: string | null,
       public ldapId?: string | null,
       public lineToken ?: string | null,
       public modifyTimestamp ?: string | null,
       public modifyUserId ?: string | null,
       public orgId ?: string | null,
       public tel1 ?: string | null,
       public tel2 ?: string | null,
       public titleId ?: string | null,
       public userId ?: string | null,
       public userName ?: string | null,
       public userEName ?: string | null,
    ) {
    }

}