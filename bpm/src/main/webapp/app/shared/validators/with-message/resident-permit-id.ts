import residentPermitId from '@/shared/validators/raw/resident-permit-id';

export default {
  $validator: residentPermitId,
  $message: '請輸入正確的身分證號/居留證字號',
};
