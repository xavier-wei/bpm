/**
 * 附件上傳物件
 */
export interface IFileModel {
    formId?: string | null;           //表單編號
    fileName?: string | null;         //附件名稱
    file?: File | null;               //附件
    fileSize?: string | null;         //大小
    authorName?: string | null;       //作者姓名
    fileDescription?: string | null;  //附件說明
    filePath?: string | null;         //檔案路徑
    updateUser?: string | null;       //異動人員
    updateTime?: Date | null;         //異動日期
    createUser?: string | null;       //建檔人員
    createTime?: Date | null;         //建檔日期
}


export class FileModel implements IFileModel {
    constructor(
        public formId                   ?: string | null,     //表單編號
        public fileName                 ?: string | null,     //附件名稱
        public file                     ?: File | null,       //附件
        public fileSize                 ?: string | null,     //大小
        public authorName               ?: string | null,     //作者姓名
        public fileDescription          ?: string | null,     //附件說明
        public filePath                 ?: string | null,     //檔案路徑
        public updateUser               ?: string | null,     //異動人員
        public updateTime               ?: Date | null,       //異動日期
        public createUser               ?: string | null,     //建檔人員
        public createTime               ?: Date | null        //建檔日期
    ) {
    }

}
