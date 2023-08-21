
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
        case 'SeniorTechSpecialist':
            return '資訊小組簡任技正/科長';
        case 'ServerRoomOperator':
            return '機房操作人員';
        case 'ReviewStaff':
            return '複核人員';
        case 'ServerRoomManager':
            return '機房管理人員';
        default:
            return '';
    }
}


export function changeUnit(unit: any): string {

    switch (unit) {
        case 'ApplyTester':
            return '企劃處/第三科';
        case 'ChiefTester':
            return '企劃處/第三科';
        case 'DirectorTester':
            return '企劃處';
        case 'InfoTester':
            return '資訊推動小組';
        case 'SeniorTechSpecialist':
            return '資訊推動小組';
        case 'ServerRoomOperator':
            return '機房人員';
        case 'ReviewStaff':
            return '機房人員';
        case 'ServerRoomManager':
            return '機房人員';
        default:
            return '';
    }
}
