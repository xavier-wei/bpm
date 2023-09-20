create table BPM_SIGNER_LIST
(
    FORM_ID     varchar(50)                                       not null,
    DEPT_ID     varchar(50)                                       not null,
    TASK_NAME   nvarchar(100) collate Chinese_Taiwan_Stroke_CS_AS not null,
    EMP_IDS     varchar(100)                                      not null,
    EMP_NAMES   nvarchar(100) collate Chinese_Taiwan_Stroke_CS_AS not null,
    CREATE_TIME datetime                                          not null,
    constraint SIGNER_LIST_pk
        primary key (FORM_ID, TASK_NAME)
)
go
