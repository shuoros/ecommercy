/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OrderDetailComponent from '@/entities/order/order-details.vue';
import OrderClass from '@/entities/order/order-details.component';
import OrderService from '@/entities/order/order.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Order Management Detail Component', () => {
    let wrapper: Wrapper<OrderClass>;
    let comp: OrderClass;
    let orderServiceStub: SinonStubbedInstance<OrderService>;

    beforeEach(() => {
      orderServiceStub = sinon.createStubInstance<OrderService>(OrderService);

      wrapper = shallowMount<OrderClass>(OrderDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { orderService: () => orderServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOrder = { id: 123 };
        orderServiceStub.find.resolves(foundOrder);

        // WHEN
        comp.retrieveOrder(123);
        await comp.$nextTick();

        // THEN
        expect(comp.order).toBe(foundOrder);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOrder = { id: 123 };
        orderServiceStub.find.resolves(foundOrder);

        // WHEN
        comp.beforeRouteEnter({ params: { orderId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.order).toBe(foundOrder);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
