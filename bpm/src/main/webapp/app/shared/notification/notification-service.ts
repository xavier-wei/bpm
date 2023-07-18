import { BButtonClose } from 'bootstrap-vue';

export default class NotificationService {
  private $root;

  private position = 'b-toaster-bottom-right';

  private defaultDelay = 5000;
  private count = 0;

  constructor($root) {
    this.$root = $root;
  }

  info(message: string) {
    this.makeModal(message, 'info');
  }

  danger(message: string) {
    this.makeModal(message, 'danger');
  }

  warning(message: string) {
    this.makeModal(message, 'warning');
  }

  success(message: string) {
    this.makeModal(message, 'info');
  }

  dangerCallback(message: string, callback: any) {
    this.makeModalCallback(message, 'danger', callback);
  }

  confirmCallback(message: string, callback: any) {
    this.makeModalConfirmCallback(message, 'info', callback);
  }

  private makeToast(message: string, variant: string, delay: number = this.defaultDelay) {
    const h = this.$root.$createElement;
    const id = `my-toast-${this.count++}`;
    const bvToast = this.$root.$bvToast;

    const messageNode = h('div', [
      message,
      h(BButtonClose, {
        staticClass: 'ml-auto mb-1',
        on: {
          click: () => {
            bvToast.hide(id);
          },
        },
      }),
    ]);

    bvToast.toast(messageNode, {
      id,
      variant,
      toaster: this.position,
      solid: true,
      appendToast: true,
      autoHideDelay: delay,
      noCloseButton: true,
    });
  }

  private makeModal(message: string, variant: string) {
    const bvModal = this.$root.$bvModal;

    const h = this.$root.$createElement;

    const pattern = /.*\n.*/;
    const messageList = [];
    //message裡面有換行
    if (pattern.test(message)) {
      const messageArr = message.split('\n');
      messageArr.forEach(oneMessage => {
        messageList.push(h('p', { class: ['text-left'] }, [oneMessage]));
      });
    } else {
      messageList.push(h('p', { class: ['text-left'] }, [message]));
    }
    const messageVNode = h('div', [...messageList]);
    // const titleVNode = h('div', { domProps: { innerHTML: 'Title from <i>HTML<i> string' } })
    // bvModal.msgBoxOk(message, {
    //   headerBgVariant: variant,
    //   headerTextVariant: 'light',
    //   titleTag: 'h4',
    //   size: 'lg',
    //   title: '系統訊息',
    //   headerClass: 'p-2 justify-content-center font-weight-bolder',
    //   bodyClass: 'h5',
    //   footerClass: 'justify-content-center',
    //   okTitle: '確定',
    // });
    bvModal.msgBoxOk([messageVNode], {
      headerBgVariant: variant,
      headerTextVariant: 'light',
      titleTag: 'h4',
      size: 'lg',
      title: '系統訊息',
      headerClass: 'p-2 justify-content-center font-weight-bolder',
      bodyClass: 'h5',
      footerClass: 'justify-content-center',
      okTitle: '確定',
      buttonSize: 'sm',
    });
  }

  private makeModalCallback(message: string, variant: string, callback: any) {
    const bvModal = this.$root.$bvModal;
    console.log(variant);
    const h = this.$root.$createElement;

    const pattern = /.*\n.*/;
    const messageList = [];
    //message裡面有換行
    if (pattern.test(message)) {
      const messageArr = message.split('\n');
      messageArr.forEach(oneMessage => {
        messageList.push(h('p', { class: ['text-left'] }, [oneMessage]));
      });
    } else {
      messageList.push(h('p', { class: ['text-left'] }, [message]));
    }
    const messageVNode = h('div', [...messageList]);

    bvModal
      .msgBoxOk([messageVNode], {
        headerBgVariant: variant,
        headerTextVariant: 'light',
        titleTag: 'h4',
        size: 'lg',
        title: '系統訊息',
        headerClass: 'p-2 justify-content-center font-weight-bolder',
        bodyClass: 'h5',
        footerClass: 'justify-content-center',
        okTitle: '確定',
        buttonSize: 'sm',
      })
      .then(trigger => {
        callback();
      });
  }

  private makeModalConfirmCallback(message: string, variant: string, callback: any) {
    const bvModal = this.$root.$bvModal;
    console.log(variant);
    const h = this.$root.$createElement;

    const pattern = /.*\n.*/;
    const messageList = [];
    //message裡面有換行
    if (pattern.test(message)) {
      const messageArr = message.split('\n');
      messageArr.forEach(oneMessage => {
        messageList.push(h('p', { class: ['text-left'] }, [oneMessage]));
      });
    } else {
      messageList.push(h('p', { class: ['text-left'] }, [message]));
    }
    const messageVNode = h('div', [...messageList]);

    bvModal
      .msgBoxConfirm([messageVNode], {
        headerBgVariant: variant,
        headerTextVariant: 'light',
        titleTag: 'h4',
        size: 'lg',
        title: '系統訊息',
        headerClass: 'p-2 justify-content-center font-weight-bolder',
        bodyClass: 'h5',
        footerClass: 'justify-content-center',
        okTitle: '確定',
        cancelTitle: '取消',
        buttonSize: 'sm',
      })
      .then(trigger => {
        callback(trigger);
      });
  }
}
