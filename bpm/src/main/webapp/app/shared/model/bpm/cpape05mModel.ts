export interface ICpape05mModel {
    crkcodName?: string | null;
    ctAreaCode?: string | null;
    ctExt?: string | null;
    ctHomeAddr?: string | null;
    ctMobile?: string | null;
    ctTel?: string | null;
    email?: string | null;
    loginId?: string | null;
    memcodName?: string | null;
    nationality?: number | null;
    orgName?: string | null;
    peactdate?: Date | null;
    pearea?: number | null;
    pebirthd?: string | null;
    pecard?: string | null;
    pecrkcod?: string | null;
    pefstdate?: string | null;
    pehday?: number | null;
    pehmon?: string | null;
    pehmon2?: string | null;
    pehyear?: string | null;
    pehyear2?: string | null;
    peidno?: string | null;
    pelevdate?: string | null;
    pememcod?: string | null;
    pename?: string | null;
    peorg?: string | null;
    peoverhfee?: number | null;
    pepoint?: string | null;
    perday?: number | null;
    perday1?: number | null;
    perday2?: number | null;
    pesex?: string | null;
    petit?: string | null;
    petraining?: string | null;
    peunit?: string | null;
    peupdate?: string | null;
    peykind?: string | null;
    title?: string | null;
    unitName?: string | null;
}

export class Cpape05mModel implements ICpape05mModel {
    constructor(
        public crkcodName?: string | null,
        public ctAreaCode?: string | null,
        public ctExt?: string | null,
        public ctHomeAddr?: string | null,
        public ctMobile?: string | null,
        public ctTel?: string | null,
        public email?: string | null,
        public loginId?: string | null,
        public memcodName?: string | null,
        public nationality?: number | null,
        public orgName?: string | null,
        public peactdate?: Date | null,
        public pearea?: number | null,
        public pebirthd?: string | null,
        public pecard?: string | null,
        public pecrkcod?: string | null,
        public pefstdate?: string | null,
        public pehday?: number | null,
        public pehmon?: string | null,
        public pehmon2?: string | null,
        public pehyear?: string | null,
        public pehyear2?: string | null,
        public peidno?: string | null,
        public pelevdate?: string | null,
        public pememcod?: string | null,
        public pename?: string | null,
        public peorg?: string | null,
        public peoverhfee?: number | null,
        public pepoint?: string | null,
        public perday?: number | null,
        public perday1?: number | null,
        public perday2?: number | null,
        public pesex?: string | null,
        public petit?: string | null,
        public petraining?: string | null,
        public peunit?: string | null,
        public peupdate?: string | null,
        public peykind?: string | null,
        public title?: string | null,
        public unitName?: string | null,
    ) {
    }

}
