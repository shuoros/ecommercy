/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CouponComponent from '@/entities/coupon/coupon.vue';
import CouponClass from '@/entities/coupon/coupon.component';
import CouponService from '@/entities/coupon/coupon.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Coupon Management Component', () => {
    let wrapper: Wrapper<CouponClass>;
    let comp: CouponClass;
    let couponServiceStub: SinonStubbedInstance<CouponService>;

    beforeEach(() => {
      couponServiceStub = sinon.createStubInstance<CouponService>(CouponService);
      couponServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CouponClass>(CouponComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          couponService: () => couponServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      couponServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCoupons();
      await comp.$nextTick();

      // THEN
      expect(couponServiceStub.retrieve.called).toBeTruthy();
      expect(comp.coupons[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      couponServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(couponServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCoupon();
      await comp.$nextTick();

      // THEN
      expect(couponServiceStub.delete.called).toBeTruthy();
      expect(couponServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
