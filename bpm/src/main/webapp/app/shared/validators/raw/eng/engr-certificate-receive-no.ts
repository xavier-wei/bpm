import { IEngEngrCertificate } from "@/shared/model/eng/eng-engr-certificate.model";
import axios from "axios";

export default function (id: () => string, version: () => number) {
  return async (receiveNo: string) => {
    const data = await checkReceiveNo(receiveNo);
    return !receiveNo || !data.find(i => i.engEngrCertificateId !== id() || i.engEngrCertificateVersion !== version());
  }
}

function checkReceiveNo(receiveNo: string): Promise<IEngEngrCertificate[]> {
  return new Promise<IEngEngrCertificate[]>((resolve) => {
    axios
      .get(`/eng-engr-certificates/check-receive-no/${receiveNo}`)
      .then(res => {
        resolve(res.data);
      });
  })
}