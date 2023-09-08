export function changeDirections(user: any): string {

    switch (user) {
        case 'applierConfirm':
            return '申請者';
        case 'sectionChiefDecision':
            return '申請人所屬之科長';
        case 'directorDecision':
            return '申請人所屬之單位主管';
        case 'infoGroupDecision':
            return '資訊小組資安承辦人';
        case 'seniorTechSpecialistSign':
            return '資訊小組簡任技正/科長';
        case 'serverRoomOperatorSetting':
            return '機房操作人員';
        case 'reviewStaff':
            return '複核人員';
        case 'serverRoomManager':
            return '機房管理人員';
        default:
            return '';
    }
}
export function changeDealWithUnit(unit: any, unitList: any): string {
    if (unit == null) return '';
    let unitName = '';
    unitList.forEach(data => {

        if (data.value === unit) {
            unitName = data.text
        }
    })
    return unitName;
}


