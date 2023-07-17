import { inject } from '@vue/composition-api';
import { BvModal } from 'bootstrap-vue';

export function useBvModal(): BvModal {
  return inject<BvModal>('$bvModal');
}
