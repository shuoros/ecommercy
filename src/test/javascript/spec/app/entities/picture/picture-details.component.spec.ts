/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PictureDetailComponent from '@/entities/picture/picture-details.vue';
import PictureClass from '@/entities/picture/picture-details.component';
import PictureService from '@/entities/picture/picture.service';
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
  describe('Picture Management Detail Component', () => {
    let wrapper: Wrapper<PictureClass>;
    let comp: PictureClass;
    let pictureServiceStub: SinonStubbedInstance<PictureService>;

    beforeEach(() => {
      pictureServiceStub = sinon.createStubInstance<PictureService>(PictureService);

      wrapper = shallowMount<PictureClass>(PictureDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { pictureService: () => pictureServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPicture = { id: 123 };
        pictureServiceStub.find.resolves(foundPicture);

        // WHEN
        comp.retrievePicture(123);
        await comp.$nextTick();

        // THEN
        expect(comp.picture).toBe(foundPicture);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPicture = { id: 123 };
        pictureServiceStub.find.resolves(foundPicture);

        // WHEN
        comp.beforeRouteEnter({ params: { pictureId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.picture).toBe(foundPicture);
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
