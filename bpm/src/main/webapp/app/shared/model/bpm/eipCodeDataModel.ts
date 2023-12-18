/**
 * 職稱物件
 */
export interface IEipCodeDataModel {
    codekind  ?: string | null; //主代號類別
    codeno    ?: string | null; //主代號
    scodekind ?: string | null; //主代號
    scodeno   ?: string | null; //副代號
    codename  ?: string | null; //主代號名稱
    staff     ?: string | null; //異動者代號
    prcdat    ?: Date | null;   //異動者代號
    remark    ?: string | null; //說明
}


export class EipCodeDataModel implements IEipCodeDataModel {
    constructor(
        public codekind        ?: string | null,   //主代號類別
        public codeno          ?: string | null,   //主代號
        public scodekind       ?: string | null,   //主代號
        public scodeno         ?: string | null,   //副代號
        public codename        ?: string | null,   //主代號名稱
        public staff           ?: string | null,   //異動者代號
        public prcdat          ?: Date | null,     //異動者代號
        public remark          ?: string | null,   //說明
    ) {
    }

}
