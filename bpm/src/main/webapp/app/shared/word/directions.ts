export function changeDirections(user: any): string {

    switch (user) {
        case 'ApplyTester':
            return '申請者';
        case 'ChiefTester':
            return '申請人所屬之科長';
        case 'DirectorTester':
            return '申請人所屬之單位主管';
        case 'InfoTester':
            return '資訊小組資安承辦人';
        case 'seniorTechSpecialistTester':
            return '資訊小組簡任技正/科長';
        case 'serverRoomOperatorTester':
            return '機房操作人員';
        case 'reviewStaffTester':
            return '複核人員';
        case 'serverRoomManagerTester':
            return '機房管理人員';
        default:
            return '';
    }
}


export function changeUnit(unit: any): string {

    switch (unit) {
        case 'ApplyTester':
            return '6';
        case 'ChiefTester':
            return '6';
        case 'DirectorTester':
            return '6';
        case 'InfoTester':
            return '15';
        case 'seniorTechSpecialistTester':
            return '15';
        case 'serverRoomOperatorTester':
            return '7';
        case 'reviewStaffTester':
            return '7';
        case 'serverRoomManagerTester':
            return '7';
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


