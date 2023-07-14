import { MemberTypeEnum } from "@/shared/enum";
import axios from "axios";

export default async function (idno: string) {
  const data = await queryEngMember(idno);
  return !!data;
}

function queryEngMember(idno: string) {
  if (!!idno && idno.length === 10) {
    return new Promise((resolve) => {
      axios
        .get(`/eng-members/${MemberTypeEnum.ENGR}/${idno}`)
        .then(res => {
          resolve(res.data);
        });
    })
  }
}