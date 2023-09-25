-- auto-generated definition
create table BPM_ISMS_L410
(
    FORM_ID                          varchar(50) not null
        constraint BPM_ISMS_L410_pk
            primary key,
    PROCESS_INSTANCE_ID              varchar(50) not null,
    APPLY_DATE                       datetime    not null,
    FIL_EMPID                        nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    FIL_NAME                         nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    FIL_UNIT                         nvarchar(100) collate Chinese_Taiwan_Stroke_CS_AS not null,
    APP_EMPID                        nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    APP_NAME                         nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    APP_ENG_NAME                     nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    APP_UNIT                         nvarchar(30) collate Chinese_Taiwan_Stroke_CS_AS not null,
    POSITION                         nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    EXT_NUM                          nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_SUBMIT                        nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    APP_REASON                       nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    IS_ENABLE_DATE                   nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    ENABLE_DATE                      datetime,
    IS_OTHER                         nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_REASON                     nvarchar(100) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_HR_SYS                        nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    HR_SYS                           nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    HR_SYS_CHANGE                    nvarchar(100) collate Chinese_Taiwan_Stroke_CS_AS,
    HR_SYS_ADM_UNIT                  nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    HR_SYS_STATUS                    nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    HR_SYS_ENABLE_DATE               datetime,
    HR_SYS_ADM_NAME                  nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_AD_SYS                        nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    AD_ACCOUNT                       nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    AD_SYS                           nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    AD_SYS_CHANGE                    nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    AD_SYS_ADM_UNIT                  nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    AD_SYS_STATUS                    nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    AD_SYS_ENABLE_DATE               datetime,
    AD_SYS_ADM_NAME                  nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_MEETING_ROOM                  nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    MEETING_ROOM                     nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    MEETING_ROOM_CHANGE              nvarchar(100) collate Chinese_Taiwan_Stroke_CS_AS,
    MEETING_ROOM_ADM_UNIT            nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    MEETING_ROOM_STATUS              nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    MEETING_ROOM_ENABLE_DATE         datetime,
    MEETING_ROOM_ADM_NAME            nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_OD_SYS                        nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    OD_SYS_ROLE                      nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OD_SYS                           nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    OD_SYS_OTHER                     nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OD_SYS_ADM_UNIT                  nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OD_SYS_STATUS                    nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OD_SYS_ENABLE_DATE               datetime,
    OD_SYS_ADM_NAME                  nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_EMAIL_SYS                     nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    EMAIL_SYS_ACCOUNT                nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    EMAIL_SYS                        nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    EMAIL_APPLY1                     nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    EMAIL_APPLY2                     nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    EMAIL_SYS_CHANGE                 nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    EMAIL_SYS_ADM_UNIT               nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    EMAIL_SYS_STATUS                 nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    EMAIL_SYS_ENABLE_DATE            datetime,
    EMAIL_SYS_ADM_NAME               nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_WEB_SITE                      nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    IS_PCC_WWW                       nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    IS_PCC_HOME                      nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    WEB_SITE                         nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    IS_UNIT_ADM                      nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    IS_UNIT_DATA_MGR                 nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    IS_WEB_SITE_OTHER                nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    WEB_SITE_OTHER                   nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    WEB_SITE_ADM_UNIT                nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    WEB_SITE_STATUS                  nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    WEB_SITE_ENABLE_DATE             datetime,
    WEB_SITE_ADM_NAME                nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_PCC_PIS                       nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    PCC_PIS_ACCOUNT                  nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    PCC_PIS                          nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    PCC_PIS_CHANGE                   nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    PCC_PIS_ADM_UNIT                 nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    PCC_PIS_STATUS                   nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    PCC_PIS_ENABLE_DATE              datetime,
    PCC_PIS_ADM_NAME                 nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_ENG_AND_PRJ_INFO_SYS          nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    ENG_AND_PRJ_INFO_SYS_ACCOUNT     nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    ENG_AND_PRJ_INFO_SYS             nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    ENG_AND_PRJ_INFO_SYS_CHANGE      nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    ENG_AND_PRJ_INFO_SYS_ADM_UNIT    nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    ENG_AND_PRJ_INFO_SYS_STATUS      nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    ENG_AND_PRJ_INFO_SYS_ENABLE_DATE datetime,
    ENG_AND_PRJ_INFO_SYS_ADM_NAME    nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_REV_SYS                       nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    REV_SYS_ACCOUNT                  nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    REV_SYS                          nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    REV_SYS_CHANGE                   nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    REV_SYS_ADM_UNIT                 nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    REV_SYS_STATUS                   nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    REV_SYS_ENABLE_DATE              datetime,
    REV_SYS_ADM_NAME                 nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_REC_SYS                       nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    REC_SYS_ACCOUNT                  nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    REC_SYS                          nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    REC_SYS_CHANGE                   nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    REC_SYS_ADM_UNIT                 nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    REC_SYS_STATUS                   nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    REC_SYS_ENABLE_DATE              datetime,
    REC_SYS_ADM_NAME                 nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_BID_SYS                       nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    BID_SYS_ACCOUNT                  nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    BID_SYS                          nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    BID_SYS_CHANGE                   nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    BID_SYS_ADM_UNIT                 nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    BID_SYS_STATUS                   nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    BID_SYS_ENABLE_DATE              datetime,
    BID_SYS_ADM_NAME                 nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_OTHER_SYS1                    nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS1_SERVER_NAME           nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS1_ACCOUNT               nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS1                       nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS1_CHANGE                nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS1_ADM_UNIT              nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS1_STATUS                nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS1_ENABLE_DATE           datetime,
    OTHER_SYS1_ADM_NAME              nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_OTHER_SYS2                    nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS2_SERVER_NAME           nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS2_ACCOUNT               nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS2                       nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS2_CHANGE                nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS2_ADM_UNIT              nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS2_STATUS                nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS2_ENABLE_DATE           datetime,
    OTHER_SYS2_ADM_NAME              nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_OTHER_SYS3                    nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS3_SERVER_NAME           nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS3_ACCOUNT               nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS3                       nvarchar collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS3_CHANGE                nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS3_ADM_UNIT              nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS3_STATUS                nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_SYS3_ENABLE_DATE           datetime,
    OTHER_SYS3_ADM_NAME              nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    PROCESS_INSTANCE_STATUS          nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    UPDATE_USER                      nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS,
    UPDATE_TIME                      datetime,
    CREATE_USER                      nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    CREATE_TIME                      datetime    not null
) go

exec sp_addextendedproperty 'MS_Description', N'L410-共用系統使用者帳號申請單', 'SCHEMA', 'dbo', 'TABLE',
     'BPM_ISMS_L410'
go

exec sp_addextendedproperty 'MS_Description', N'表單編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'FORM_ID'
go

exec sp_addextendedproperty 'MS_Description', N'流程實體編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'PROCESS_INSTANCE_ID'
go

exec sp_addextendedproperty 'MS_Description', N'申請日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'APPLY_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'填表人員工編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'FIL_EMPID'
go

exec sp_addextendedproperty 'MS_Description', N'填表人姓名', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'FIL_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'填表人單位名稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'FIL_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'申請人員工編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'APP_EMPID'
go

exec sp_addextendedproperty 'MS_Description', N'申請人姓名', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'APP_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'申請人英文姓名', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'APP_ENG_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'申請人單位別', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'APP_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'職稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN', 'POSITION'
go

exec sp_addextendedproperty 'MS_Description', N'電話分機', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'EXT_NUM'
go

exec sp_addextendedproperty 'MS_Description', N'是否暫存、送出', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'IS_SUBMIT'
go

exec sp_addextendedproperty 'MS_Description', N'申請事由', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'APP_REASON'
go

exec sp_addextendedproperty 'MS_Description', N'是否有生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'IS_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'是否有其他理由', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'IS_OTHER'
go

exec sp_addextendedproperty 'MS_Description', N'其他理由', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_REASON'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請人事差勤系統', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_HR_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'人事差勤系統處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'HR_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'人事差勤系統處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE',
     'BPM_ISMS_L410', 'COLUMN', 'HR_SYS_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'人事差勤系統管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'HR_SYS_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'人事差勤系統處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'HR_SYS_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'人事差勤系統生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'HR_SYS_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'人事差勤系統處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'HR_SYS_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請AD系統帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_AD_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'申請的AD帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'AD_ACCOUNT'
go

exec sp_addextendedproperty 'MS_Description', N'AD系統處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'AD_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'AD系統處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'AD_SYS_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'AD系統管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'AD_SYS_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'AD系統處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'AD_SYS_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'AD系統生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'AD_SYS_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'AD系統處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'AD_SYS_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請公文系統帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_OD_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請會議室管理系統', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_MEETING_ROOM'
go

exec sp_addextendedproperty 'MS_Description', N'會議室管理系統處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'MEETING_ROOM'
go

exec sp_addextendedproperty 'MS_Description', N'會議室管理系統處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE',
     'BPM_ISMS_L410', 'COLUMN', 'MEETING_ROOM_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'會議室管理系統管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'MEETING_ROOM_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'會議室管理系統處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'MEETING_ROOM_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'會議室管理系統生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'MEETING_ROOM_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'會議室管理系統處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'MEETING_ROOM_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'公文管理系統角色', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OD_SYS_ROLE'
go

exec sp_addextendedproperty 'MS_Description', N'公文系統處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OD_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'公文系統處理權限其他資料', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'OD_SYS_OTHER'
go

exec sp_addextendedproperty 'MS_Description', N'公文系統管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OD_SYS_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'公文系統處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OD_SYS_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'公文系統生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OD_SYS_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'公文系統處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OD_SYS_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請電子郵件帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_EMAIL_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'電子郵件帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'EMAIL_SYS_ACCOUNT'
go

exec sp_addextendedproperty 'MS_Description', N'電子郵件處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'EMAIL_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'申請帳號1', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'EMAIL_APPLY1'
go

exec sp_addextendedproperty 'MS_Description', N'申請帳號2', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'EMAIL_APPLY2'
go

exec sp_addextendedproperty 'MS_Description', N'電子郵件系統處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE',
     'BPM_ISMS_L410', 'COLUMN', 'EMAIL_SYS_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'電子郵件系統管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'EMAIL_SYS_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'電子郵件系統處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'EMAIL_SYS_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'電子郵件系統生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'EMAIL_SYS_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'電子郵件系統處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'EMAIL_SYS_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請網站帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'IS_WEB_SITE'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請全球資訊網帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_PCC_WWW'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請會內資訊網站帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_PCC_HOME'
go

exec sp_addextendedproperty 'MS_Description', N'網站帳號權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'WEB_SITE'
go

exec sp_addextendedproperty 'MS_Description', N'是否是部門管理者', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'IS_UNIT_ADM'
go

exec sp_addextendedproperty 'MS_Description', N'是否是部門資料維護者', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_UNIT_DATA_MGR'
go

exec sp_addextendedproperty 'MS_Description', N'是否是網站帳號其他', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_WEB_SITE_OTHER'
go

exec sp_addextendedproperty 'MS_Description', N'網站帳號其他內容', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'WEB_SITE_OTHER'
go

exec sp_addextendedproperty 'MS_Description', N'網站帳號管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'WEB_SITE_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'網站帳號處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'WEB_SITE_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'網站帳號生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'WEB_SITE_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'網站帳號處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'WEB_SITE_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請政府電子採購網帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_PCC_PIS'
go

exec sp_addextendedproperty 'MS_Description', N'申請的政府電子採購網帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'PCC_PIS_ACCOUNT'
go

exec sp_addextendedproperty 'MS_Description', N'政府電子採購網處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'PCC_PIS'
go

exec sp_addextendedproperty 'MS_Description', N'政府電子採購網處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE',
     'BPM_ISMS_L410', 'COLUMN', 'PCC_PIS_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'政府電子採購網管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'PCC_PIS_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'政府電子採購網處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'PCC_PIS_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'政府電子採購網生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'PCC_PIS_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'政府電子採購網處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'PCC_PIS_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'技師與工程技術顧問公司管理資訊系統', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_ENG_AND_PRJ_INFO_SYS'
go


exec sp_addextendedproperty 'MS_Description', N'申請的技師與工程技術顧問公司管理資訊系統帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'ENG_AND_PRJ_INFO_SYS_ACCOUNT'
go

exec sp_addextendedproperty 'MS_Description', N'技師與工程技術顧問公司管理資訊系統處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'ENG_AND_PRJ_INFO_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'技師與工程技術顧問公司管理資訊系統處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'ENG_AND_PRJ_INFO_SYS_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'技師與工程技術顧問公司管理資訊系統管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'ENG_AND_PRJ_INFO_SYS_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'技師與工程技術顧問公司管理資訊系統處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'ENG_AND_PRJ_INFO_SYS_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'技師與工程技術顧問公司管理資訊系統生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'ENG_AND_PRJ_INFO_SYS_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'技師與工程技術顧問公司管理資訊系統處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'ENG_AND_PRJ_INFO_SYS_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請公共工程案件審議資訊系統帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_REV_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'申請的公共工程案件審議資訊系統帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'REV_SYS_ACCOUNT'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程案件審議資訊系統處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'REV_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程案件審議資訊系統處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'REV_SYS_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程案件審議資訊系統管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'REV_SYS_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程案件審議資訊系統處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'REV_SYS_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程案件審議資訊系統生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'REV_SYS_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程案件審議資訊系統處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'REV_SYS_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請災後復建工程經費審議及執行資訊系統帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_REC_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'申請的災後復建工程經費審議及執行資訊系統帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'REC_SYS_ACCOUNT'
go

exec sp_addextendedproperty 'MS_Description', N'災後復建工程經費審議及執行資訊系統處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'REC_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'災後復建工程經費審議及執行資訊系統處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'REC_SYS_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'災後復建工程經費審議及執行資訊系統管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'REC_SYS_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'災後復建工程經費審議及執行資訊系統處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'REC_SYS_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'災後復建工程經費審議及執行資訊系統生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'REC_SYS_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'災後復建工程經費審議及執行資訊系統處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'REC_SYS_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請公共工程標案管理系統帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_BID_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'申請的公共工程標案管理系統帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'BID_SYS_ACCOUNT'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程標案管理系統處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'BID_SYS'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程標案管理系統處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'BID_SYS_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程標案管理系統管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'BID_SYS_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程標案管理系統處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'BID_SYS_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程標案管理系統生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'BID_SYS_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'公共工程標案管理系統處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'BID_SYS_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請其他系統1帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_OTHER_SYS1'
go

exec sp_addextendedproperty 'MS_Description', N'申請的其他系統1名稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'OTHER_SYS1_SERVER_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'申請的其他系統1帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'OTHER_SYS1_ACCOUNT'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統1處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS1'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統1處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'OTHER_SYS1_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統1管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS1_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統1處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS1_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統1生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS1_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統1處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS1_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請其他系統2帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_OTHER_SYS2'
go

exec sp_addextendedproperty 'MS_Description', N'申請的其他系統2名稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'OTHER_SYS2_SERVER_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'申請的其他系統2帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'OTHER_SYS2_ACCOUNT'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統2處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS2'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統2處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'OTHER_SYS2_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統2管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS2_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統2處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS2_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統2生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS2_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統2處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS2_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'是否申請其他系統3帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'IS_OTHER_SYS3'
go

exec sp_addextendedproperty 'MS_Description', N'申請的其他系統3名稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'OTHER_SYS3_SERVER_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'申請的其他系統3帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'OTHER_SYS3_ACCOUNT'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統3處理權限', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS3'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統3處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410',
     'COLUMN', 'OTHER_SYS3_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統3管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS3_ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統3處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS3_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統3生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS3_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'其他系統3處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'OTHER_SYS3_ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'流程實體狀態', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'PROCESS_INSTANCE_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'異動人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'UPDATE_USER'
go

exec sp_addextendedproperty 'MS_Description', N'異動日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'UPDATE_TIME'
go

exec sp_addextendedproperty 'MS_Description', N'建檔人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'CREATE_USER'
go

exec sp_addextendedproperty 'MS_Description', N'建檔日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L410', 'COLUMN',
     'CREATE_TIME'
go




