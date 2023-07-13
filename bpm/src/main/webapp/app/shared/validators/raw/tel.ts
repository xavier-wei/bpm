import mobileTel from "@/shared/validators/raw/mobile-tel";
import lineTel from "@/shared/validators/raw/line-tel";

export default function (isMobile: () => boolean) {
  return (value: string) => {
    return isMobile() ? mobileTel(value) : lineTel(value);
  }
}