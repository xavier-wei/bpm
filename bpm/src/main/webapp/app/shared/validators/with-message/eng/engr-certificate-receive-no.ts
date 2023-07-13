import engrCertificateReceiveNo from '../../raw/eng/engr-certificate-receive-no'

export default function (id: () => string, version: () => number) {
  return {
    $validator: engrCertificateReceiveNo(id, version),
    $message: '此收文文號已有其他申請資料'
  };
};