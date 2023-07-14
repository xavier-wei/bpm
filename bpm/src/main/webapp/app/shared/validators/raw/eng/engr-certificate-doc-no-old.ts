import { IEngEngrCertificate } from "@/shared/model/eng/eng-engr-certificate.model";
import axios from "axios";

export default async (value: string) => {
  const data = await checkEngrCertificateDocNoOld(value);
  return !value || data.length === 0;
}

function checkEngrCertificateDocNoOld(value: string): Promise<IEngEngrCertificate[]> {
  return new Promise<IEngEngrCertificate[]>((resolve) => {
    axios
      .get(`/eng-engr-certificates/check-engr-certificate-doc-no-old/${value}`)
      .then(res => {
        resolve(res.data);
      });
  })
}